import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivityService } from '../../services/activity';
import { Activity } from '../../models/activity.model';

@Component({
  selector: 'app-activity-feed',
  standalone:true,
  imports:[CommonModule],
  templateUrl:'./activity-feed.html',
  styleUrls:['./activity-feed.css']
})
export class ActivityFeedComponent implements OnInit{

  activities:Activity[]=[];

  constructor(private activityService:ActivityService){}

  ngOnInit():void{

    this.loadActivities();

  }

  loadActivities(){

    this.activityService.getActivities().subscribe({

      next:(data)=>{

        this.activities=data;

      },

      error:(err)=>{

        console.error("Activity load failed",err);

      }

    });

  }

  getIconClass(type:string){

    if(!type) return 'activity-icon';

    switch(type){

      case 'COMMENT':
        return 'activity-icon comment';

      case 'STATUS':
        return 'activity-icon status';

      case 'ASSIGN':
        return 'activity-icon assign';

      case 'CREATED':
        return 'activity-icon created';

      case 'PRIORITY':
        return 'activity-icon priority';

      case 'DELETED':
        return 'activity-icon deleted';

      default:
        return 'activity-icon';

    }

  }

}