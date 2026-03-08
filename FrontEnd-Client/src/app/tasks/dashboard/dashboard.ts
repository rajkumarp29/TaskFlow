import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TaskService } from '../../services/task';
import { TaskFormComponent } from '../task-form/task.form';
import { Task } from '../../models/task.model';

import { Chart, registerables } from 'chart.js';
import { ActivityFeedComponent } from "../activity-feed/activity-feed";


Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    TaskFormComponent,
    ActivityFeedComponent
],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent implements OnInit {

  tasks: Task[] = [];
  filteredTasks: Task[] = [];

  selectedStatus: string = 'ALL';
  searchText: string = '';

  showModal: boolean = false;
  editingTask: Task | null = null;

  overdueCount = 0;
  todayCount = 0;

  showAnalytics = false;

  statusChart: any;
  priorityChart: any;

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {

    this.taskService.getTasks().subscribe({
      next: (data: Task[]) => {

        this.tasks = data || [];

        this.calculateAlerts();
        this.applyFilters();

      },
      error: (err) => console.error('Failed to load tasks', err)
    });

  }

  /* ALERT COUNTS */

  calculateAlerts(): void {

    const today = new Date();
    today.setHours(0,0,0,0);

    this.overdueCount = 0;
    this.todayCount = 0;

    this.tasks.forEach(task => {

      if(!task.dueDate || task.status === 'DONE') return;

      const due = new Date(task.dueDate);
      due.setHours(0,0,0,0);

      if(due < today) this.overdueCount++;

      if(due.getTime() === today.getTime()) this.todayCount++;

    });

  }

  /* FILTERS */

  filterByStatus(status: string): void {

    this.selectedStatus = status;
    this.applyFilters();

  }

  filterAssignedToMe(): void {

    const currentUserId = 2;

    this.filteredTasks = this.tasks.filter(
      task => task.assignedTo?.id === currentUserId
    );

  }

  applyFilters(): void {

    this.filteredTasks = this.tasks.filter(task => {

      const statusMatch =
        this.selectedStatus === 'ALL' ||
        task.status === this.selectedStatus;

      const searchMatch =
        task.title?.toLowerCase().includes(this.searchText.toLowerCase());

      return statusMatch && searchMatch;

    });

  }

  /* SORT */

  sortByPriority(): void {

    const order: any = {
      HIGH: 1,
      MEDIUM: 2,
      LOW: 3
    };

    this.filteredTasks.sort(
      (a,b) => order[a.priority] - order[b.priority]
    );

  }

  /* DUE STATE */

  getDueState(task: Task): string {

    if(task.status === 'DONE') return 'done';

    if(!task.dueDate) return 'upcoming';

    const today = new Date();
    const due = new Date(task.dueDate);

    today.setHours(0,0,0,0);
    due.setHours(0,0,0,0);

    if(due < today) return 'overdue';

    if(due.getTime() === today.getTime()) return 'today';

    return 'upcoming';

  }

  /* ANALYTICS TOGGLE */

  toggleAnalytics() {

    this.showAnalytics = !this.showAnalytics;

    if (this.showAnalytics) {
      setTimeout(() => {
        this.loadCharts();
      }, 200);
    }

  }

  /* LOAD CHARTS */

  loadCharts() {

    const todo = this.tasks.filter(t => t.status === 'TO_DO').length;
    const progress = this.tasks.filter(t => t.status === 'IN_PROGRESS').length;
    const done = this.tasks.filter(t => t.status === 'DONE').length;

    const high = this.tasks.filter(t => t.priority === 'HIGH').length;
    const medium = this.tasks.filter(t => t.priority === 'MEDIUM').length;
    const low = this.tasks.filter(t => t.priority === 'LOW').length;

    if (this.statusChart) this.statusChart.destroy();
    if (this.priorityChart) this.priorityChart.destroy();

    this.statusChart = new Chart("statusChart", {
      type: 'doughnut',
      data: {
        labels: ['To-Do','In Progress','Done'],
        datasets: [{
          data: [todo, progress, done],
          backgroundColor: [
            '#3b82f6',
            '#f59e0b',
            '#22c55e'
          ]
        }]
      },
      options:{
        plugins:{
          legend:{position:'bottom'}
        }
      }
    });

    this.priorityChart = new Chart("priorityChart", {
      type: 'bar',
      data: {
        labels: ['HIGH','MEDIUM','LOW'],
        datasets: [{
          label:'Tasks',
          data: [high, medium, low],
          backgroundColor: [
            '#ef4444',
            '#f59e0b',
            '#22c55e'
          ]
        }]
      },
      options:{
        plugins:{
          legend:{display:false}
        }
      }
    });

  }

  /* COUNTS */

  get totalCount(): number {
    return this.tasks.length;
  }

  get todoCount(): number {
    return this.tasks.filter(t => t.status === 'TO_DO').length;
  }

  get inProgressCount(): number {
    return this.tasks.filter(t => t.status === 'IN_PROGRESS').length;
  }

  get doneCount(): number {
    return this.tasks.filter(t => t.status === 'DONE').length;
  }

  get completionRate(): number {
    if(this.totalCount === 0) return 0;
    return Math.round((this.doneCount / this.totalCount) * 100);
  }

  openCreate(): void {

    this.editingTask = null;
    this.showModal = true;

  }

  openEdit(task: Task): void {

    this.editingTask = task;
    this.showModal = true;

  }

  onSaved(): void {

    this.showModal = false;
    this.loadTasks();

  }

  deleteTask(id: number | undefined): void {

    if (!id) return;

    if (!confirm('Delete this task?')) return;

    this.taskService.deleteTask(id).subscribe({
      next: () => this.loadTasks(),
      error: (err) => console.error('Delete failed', err)
    });

  }

}