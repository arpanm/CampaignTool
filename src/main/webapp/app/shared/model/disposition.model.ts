import dayjs from 'dayjs';
import { IField } from 'app/shared/model/field.model';
import { IDispositionSubmission } from 'app/shared/model/disposition-submission.model';
import { ICampaign } from 'app/shared/model/campaign.model';

export interface IDisposition {
  id?: number;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  fields?: IField[] | null;
  dispositionSubmissions?: IDispositionSubmission[] | null;
  campaign?: ICampaign | null;
}

export const defaultValue: Readonly<IDisposition> = {
  isActive: false,
};
