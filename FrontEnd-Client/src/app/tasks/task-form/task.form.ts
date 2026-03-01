import { Component, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../../services/task';
import { Task } from '../../models/task.model';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './task.form.html',
  styleUrls: ['./task.form.css']
})
export class TaskFormComponent implements OnChanges {
close() {
throw new Error('Method not implemented.');
}

  @Input() task: Task | null = null;
  @Output() saved = new EventEmitter<void>();

  form: Task = {
    title: '',
    description: '',
    status: 'TO_DO',
    dueDate: ''
  } as Task;

  constructor(private taskService: TaskService) {}

  ngOnChanges() {
    if (this.task) {
      this.form = { ...this.task };
    }
  }

  // ✅ Save (create or update)
  saveTask() {
  console.log('SAVE CLICKED', this.form);

  if (this.task?.id) {
    this.taskService.updateTask(this.task.id, this.form)
      .subscribe(() => this.saved.emit());
  } else {
    this.taskService.createTask(this.form)
      .subscribe(() => this.saved.emit());
  }
}
}