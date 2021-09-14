import dayjs from 'dayjs';
import { ILeadAssignment } from 'app/shared/model/lead-assignment.model';
import { IDispositionSubmission } from 'app/shared/model/disposition-submission.model';
import { CallStatus } from 'app/shared/model/enumerations/call-status.model';

export interface ICall {
  id?: number;
  phone?: string | null;
  callDate?: string | null;
  followupDate?: string | null;
  notes?: string | null;
  recordingUrl?: string | null;
  status?: CallStatus | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  leadAssignment?: ILeadAssignment | null;
  dispositionSubmission?: IDispositionSubmission | null;
}

export const defaultValue: Readonly<ICall> = {};
