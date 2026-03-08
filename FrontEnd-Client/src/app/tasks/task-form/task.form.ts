import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TaskService } from '../../services/task';
import { UserService } from '../../services/user';
import { CommentSectionComponent } from '../comment-section/comment-section';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, FormsModule, CommentSectionComponent],
  templateUrl: './task.form.html',
  styleUrls: ['./task.form.css']
})
export class TaskFormComponent implements OnInit {

  @Input() task: any = null;
  @Output() saved = new EventEmitter<void>();

  users: any[] = [];

  form: any = {
    id: null,
    title: '',
    description: '',
    status: 'TO_DO',
    dueDate: '',
    priority: 'MEDIUM',
    assignedTo: null
  };

  constructor(
    private taskService: TaskService,
    private userService: UserService
  ) {}

  ngOnInit(): void {

    this.userService.getUsers().subscribe(users => {
      this.users = users;
    });

    if (this.task) {
      this.form = {
        ...this.task,
        assignedTo: this.task.assignedTo?.id || null
      };
    }
  }

  saveTask() {

    const payload = {
      ...this.form,
      assignedTo: this.form.assignedTo
        ? { id: this.form.assignedTo }
        : null
    };

    if (this.form.id) {

      this.taskService.updateTask(this.form.id, payload)
        .subscribe(() => this.saved.emit());

    } else {

      this.taskService.createTask(payload)
        .subscribe(() => this.saved.emit());

    }

  }

}