import dayjs from 'dayjs';
import { ITelecaller } from 'app/shared/model/telecaller.model';
import { InOutType } from 'app/shared/model/enumerations/in-out-type.model';

export interface ITelecallerInOut {
  id?: number;
  eventType?: InOutType | null;
  eventTime?: string | null;
  eventDate?: string | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  telecaller?: ITelecaller | null;
}

export const defaultValue: Readonly<ITelecallerInOut> = {};
