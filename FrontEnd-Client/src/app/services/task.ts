import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from '../models/task.model';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private api = 'http://localhost:8081/api/tasks';

  constructor(private http: HttpClient) {}

  getTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.api);
  }

  createTask(task: any) {
    return this.http.post(this.api, task);
  }

  updateTask(id: number, task: any) {
    return this.http.put(`${this.api}/${id}`, task);
  }

  deleteTask(id: number) {
    return this.http.delete(`${this.api}/${id}`);
  }
}