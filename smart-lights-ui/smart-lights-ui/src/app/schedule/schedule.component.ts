import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LightService } from '../services/light.service';
import { ScheduleRule, ScheduleResponse } from '../models/schedule.model';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit {

  deviceId!: string;

  // interval-based (legacy)
  interval = 15;

  // rule form fields
  startTime = '00:00';
  endTime = '00:00';
  operation: 'ON' | 'OFF' = 'ON';
  rrule = '';

  existingRules: ScheduleRule[] = [];

  constructor(
    private route: ActivatedRoute,
    private lightService: LightService
  ) { }

  ngOnInit(): void {
    this.deviceId = this.route.snapshot.paramMap.get('deviceId')!;

    this.loadSchedule();
  }

  loadSchedule() {
    this.lightService.getSchedule(this.deviceId)
      .subscribe((data: ScheduleResponse) => {
        if (data.intervalSeconds != null) {
          this.interval = data.intervalSeconds;
        }
        if (data.rules) {
          this.existingRules = data.rules;
        }
      });
  }

  updateSchedule() {
    this.lightService.updateSchedule(this.deviceId, this.interval)
      .subscribe(() => {
        alert("Interval schedule updated!");
      });
  }

  onStartOrEndChange() {
    this.generateRRule();
  }

  generateRRule() {
    if (this.startTime && this.endTime) {
      // simple daily rule with start/end
      this.rrule = `RRULE:FREQ=DAILY;DTSTART=${this.startTime};DTEND=${this.endTime}`;
    } else {
      this.rrule = '';
    }
  }

  saveRule() {
    const payload: ScheduleRule = {
      id: 0, // backend will assign
      startTime: this.startTime,
      endTime: this.endTime,
      operation: this.operation,
      rrule: this.rrule
    };

    this.lightService.addRule(this.deviceId, payload)
      .subscribe(() => {
        alert('Rule saved');
        this.loadSchedule();
      });
  }

  deleteRule(ruleId: number) {
    this.lightService.deleteRule(this.deviceId, ruleId)
      .subscribe(() => {
        this.loadSchedule();
      });
  }

}
