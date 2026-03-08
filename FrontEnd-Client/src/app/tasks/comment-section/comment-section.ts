import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CommentService } from '../../services/comment';


@Component({
  selector: 'app-comment-section',
  standalone: true,
  imports: [CommonModule, FormsModule],   // ✅ important
  templateUrl: './comment-section.html',
  styleUrls: ['./comment-section.css']
})
export class CommentSectionComponent implements OnInit {

  @Input() taskId!: number;

  comments: any[] = [];
  newComment: string = '';

  constructor(private commentService: CommentService) {}

  ngOnInit(): void {
    this.loadComments();
  }

  loadComments() {
    this.commentService.getComments(this.taskId).subscribe(data => {
      this.comments = data;
    });
  }

  postComment() {
    if (!this.newComment.trim()) return;

    this.commentService.addComment(this.taskId, this.newComment)
      .subscribe(() => {
        this.newComment = '';
        this.loadComments();
      });
  }

  deleteComment(id: number) {
    this.commentService.deleteComment(id)
      .subscribe(() => {
        this.loadComments();
      });
  }
}