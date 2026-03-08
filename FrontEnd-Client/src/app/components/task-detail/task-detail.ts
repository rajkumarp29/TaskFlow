import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Task } from '../../models/task.model';
import { CommentSectionComponent } from '../../tasks/comment-section/comment-section';

@Component({
  selector: 'app-task-detail',
  standalone: true,
   imports: [CommonModule, CommentSectionComponent],
  templateUrl: './task-detail.html',
  styleUrls: ['./task-detail.css']
})
export class TaskDetailComponent {

  @Input() task!: Task;

  @Output() close = new EventEmitter<void>();
  @Output() edit = new EventEmitter<Task>();
  @Output() delete = new EventEmitter<number>();

  editTask(){
    this.edit.emit(this.task);
  }

  deleteTask(){
    if(confirm("Delete this task?")){
      this.delete.emit(this.task.id);
    }
  }

}