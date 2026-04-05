import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Light } from '../models/light.model';
import { LightHistory } from '../models/history.model';
import { ScheduleRequest, ScheduleResponse, ScheduleRule } from '../models/schedule.model';

@Injectable({
  providedIn: 'root'
})
export class LightService {
  private apiUrl = 'http://localhost:8080/api/lights';

  constructor(private http: HttpClient) { }

  getLights(): Observable<Light[]> {
    return this.http.get<Light[]>(this.apiUrl);
  }

  pauseLight(id: string): Observable<Light> {
    return this.http.put<Light>(`${this.apiUrl}/${id}/pause`, {});
  }

  resumeLight(id: string): Observable<Light> {
    return this.http.put<Light>(`${this.apiUrl}/${id}/resume`, {});
  }

  toggleLight(id: string): Observable<Light> {
    return this.http.put<Light>(`${this.apiUrl}/${id}/toggle`, {});
  }
  getHistory(lightId: string) {
    return this.http.get<LightHistory[]>(`${this.apiUrl}/${lightId}/history`);
  }

  updateSchedule(lightId: string, interval: number) {
    return this.http.put(`${this.apiUrl}/${lightId}/schedule`, {
      intervalSeconds: interval
    });
  }

  // rule-related operations
  addRule(lightId: string, rule: ScheduleRule) {
    return this.http.post(`${this.apiUrl}/${lightId}/schedule`, rule);
  }

  deleteRule(lightId: string, ruleId: number) {
    return this.http.delete(`${this.apiUrl}/${lightId}/schedule/${ruleId}`);
  }

  getSchedule(lightId: string) {
    return this.http.get<ScheduleResponse>(`${this.apiUrl}/${lightId}/schedule`);
  }

}
