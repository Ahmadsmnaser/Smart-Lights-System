// used when updating the simple interval schedule
export interface ScheduleRequest {
  intervalSeconds: number;
}

// rule object returned by backend
export interface ScheduleRule {
  id: number;
  startTime: string; // HH:mm
  endTime: string;   // HH:mm
  operation: 'ON' | 'OFF';
  rrule: string;
}

// response from GET /api/lights/{id}/schedule
export interface ScheduleResponse {
  intervalSeconds?: number;
  rules?: ScheduleRule[];
}
