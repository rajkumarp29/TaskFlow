import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private apiUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) {}

  getComments(taskId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/tasks/${taskId}/comments`);
  }

  addComment(taskId: number, body: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/tasks/${taskId}/comments`, { body });
  }

  deleteComment(commentId: number) {
    return this.http.delete(`${this.apiUrl}/comments/${commentId}`);
  }
}