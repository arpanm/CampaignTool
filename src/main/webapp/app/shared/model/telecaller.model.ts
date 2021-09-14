import dayjs from 'dayjs';
import { ITelecallerInOut } from 'app/shared/model/telecaller-in-out.model';

export interface ITelecaller {
  id?: number;
  name?: string | null;
  email?: string | null;
  phone?: string | null;
  startDate?: string | null;
  endDate?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  telecallerInOuts?: ITelecallerInOut[] | null;
}

export const defaultValue: Readonly<ITelecaller> = {
  isActive: false,
};
