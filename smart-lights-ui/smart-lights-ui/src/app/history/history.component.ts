import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LightService } from '../services/light.service';
import { LightHistory } from '../models/history.model';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  history: LightHistory[] = [];
  deviceId!: string;

  constructor(
    private route: ActivatedRoute,
    private lightService: LightService
  ) {}

  ngOnInit(): void {
    this.deviceId = this.route.snapshot.paramMap.get('deviceId')!;
    this.loadHistory();
  }

  loadHistory() {
    this.lightService.getHistory(this.deviceId)
      .subscribe(data => {
        this.history = data;
      });
  }
}
