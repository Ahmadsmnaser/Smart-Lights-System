import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, interval } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { Light } from '../models/light.model';
import { LightService } from '../services/light.service';

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.css']
})
export class DevicesComponent implements OnInit, OnDestroy {
  lights: Light[] = [];
  private refreshSub?: Subscription;

  constructor(private lightService: LightService) {}

  ngOnInit(): void {
    this.loadLights();

    this.refreshSub = interval(5000)
      .pipe(switchMap(() => this.lightService.getLights()))
      .subscribe((lights) => {
        this.lights = lights;
      });
  }

  loadLights(): void {
    this.lightService.getLights().subscribe((lights) => {
      this.lights = lights;
    });
  }

  pauseLight(id: string): void {
    this.lightService.pauseLight(id).subscribe(() => this.loadLights());
  }

  resumeLight(id: string): void {
    this.lightService.resumeLight(id).subscribe(() => this.loadLights());
  }

  toggleLight(id: string): void {
    this.lightService.toggleLight(id).subscribe(() => this.loadLights());
  }

  ngOnDestroy(): void {
    this.refreshSub?.unsubscribe();
  }
}
