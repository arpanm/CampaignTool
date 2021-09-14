import dayjs from 'dayjs';
import { ICall } from 'app/shared/model/call.model';
import { IDisposition } from 'app/shared/model/disposition.model';

export interface IDispositionSubmission {
  id?: number;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  call?: ICall | null;
  disposition?: IDisposition | null;
}

export const defaultValue: Readonly<IDispositionSubmission> = {};
