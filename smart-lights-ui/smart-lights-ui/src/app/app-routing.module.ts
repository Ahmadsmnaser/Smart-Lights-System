import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DevicesComponent } from './devices/devices.component';
import { HistoryComponent } from './history/history.component';
import { ScheduleComponent } from './schedule/schedule.component';

const routes: Routes = [
  { path: '', redirectTo: 'devices', pathMatch: 'full' },
  { path: 'devices', component: DevicesComponent },
  { path: 'history/:deviceId', component: HistoryComponent },
  { path: 'schedule/:deviceId', component: ScheduleComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
