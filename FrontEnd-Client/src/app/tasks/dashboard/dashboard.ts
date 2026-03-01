import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TaskService } from '../../services/task';
import { TaskFormComponent } from '../task-form/task.form';
import { Task } from '../../models/task.model';
import { NavbarComponent } from "../../shared/navbar/navbar";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, TaskFormComponent],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent implements OnInit {

  // ===============================
  // DATA
  // ===============================
  tasks: Task[] = [];
  filteredTasks: Task[] = [];

  selectedStatus: string = 'ALL';
  searchText: string = '';

  // ===============================
  // MODAL STATE
  // ===============================
  showModal: boolean = false;
  editingTask: Task | null = null;
totalTasks: any;

  constructor(private taskService: TaskService) {}

  // ===============================
  // LIFECYCLE
  // ===============================
  ngOnInit(): void {
    this.loadTasks();
  }

  // ===============================
  // LOAD TASKS
  // ===============================
  loadTasks(): void {
    this.taskService.getTasks().subscribe({
      next: (data: Task[]) => {
        this.tasks = data || [];
        this.applyFilters();
      },
      error: (err) => {
        console.error('Failed to load tasks', err);
      }
    });
  }

  // ===============================
  // FILTER BY STATUS
  // ===============================
  filterByStatus(status: string): void {
    this.selectedStatus = status;
    this.applyFilters();
  }

  // ===============================
  // APPLY FILTERS
  // ===============================
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

  // ===============================
  // 🔥 STATS (FOR EQUAL CARDS)
  // ===============================
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

  // ===============================
  // MODAL ACTIONS
  // ===============================
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

  // ===============================
  // DELETE
  // ===============================
  deleteTask(id: number | undefined): void {
    if (!id) return;

    if (!confirm('Delete this task?')) return;

    this.taskService.deleteTask(id).subscribe({
      next: () => this.loadTasks(),
      error: (err) => console.error('Delete failed', err)
    });
  }
}