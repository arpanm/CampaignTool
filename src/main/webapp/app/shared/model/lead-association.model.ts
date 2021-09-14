import dayjs from 'dayjs';
import { ILead } from 'app/shared/model/lead.model';
import { ICampaign } from 'app/shared/model/campaign.model';

export interface ILeadAssociation {
  id?: number;
  assignmentDate?: string | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  lead?: ILead | null;
  campaign?: ICampaign | null;
}

export const defaultValue: Readonly<ILeadAssociation> = {};
