import dayjs from 'dayjs';
import { ITelecaller } from 'app/shared/model/telecaller.model';
import { ILead } from 'app/shared/model/lead.model';
import { ICall } from 'app/shared/model/call.model';

export interface ILeadAssignment {
  id?: number;
  assignmentDate?: string | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  telecaller?: ITelecaller | null;
  lead?: ILead | null;
  call?: ICall | null;
}

export const defaultValue: Readonly<ILeadAssignment> = {};
