import dayjs from 'dayjs';
import { ITelecaller } from 'app/shared/model/telecaller.model';
import { ICampaign } from 'app/shared/model/campaign.model';

export interface ITelecallerAssignment {
  id?: number;
  assignmentDate?: string | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  telecaller?: ITelecaller | null;
  campaign?: ICampaign | null;
}

export const defaultValue: Readonly<ITelecallerAssignment> = {};
