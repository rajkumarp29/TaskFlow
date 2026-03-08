import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Activity } from '../models/activity.model';

@Injectable({
  providedIn: 'root'
})
export class ActivityService {

  private API = "http://localhost:8081/api/activity";

  constructor(private http:HttpClient){}

  getActivities():Observable<Activity[]>{

    return this.http.get<Activity[]>(this.API);

  }

}