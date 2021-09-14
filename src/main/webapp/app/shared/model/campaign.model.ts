import dayjs from 'dayjs';
import { IDisposition } from 'app/shared/model/disposition.model';
import { IAttribute } from 'app/shared/model/attribute.model';
import { IClient } from 'app/shared/model/client.model';
import { CampaignType } from 'app/shared/model/enumerations/campaign-type.model';
import { CampaignApprovalStatus } from 'app/shared/model/enumerations/campaign-approval-status.model';

export interface ICampaign {
  id?: number;
  name?: string | null;
  description?: string | null;
  startDate?: string | null;
  endDate?: string | null;
  type?: CampaignType | null;
  status?: CampaignApprovalStatus | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  disposition?: IDisposition | null;
  attributes?: IAttribute[] | null;
  client?: IClient | null;
}

export const defaultValue: Readonly<ICampaign> = {
  isActive: false,
};
