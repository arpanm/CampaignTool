import dayjs from 'dayjs';
import { IAttributePossibleValue } from 'app/shared/model/attribute-possible-value.model';
import { IAttributeKey } from 'app/shared/model/attribute-key.model';
import { ILead } from 'app/shared/model/lead.model';
import { ICampaign } from 'app/shared/model/campaign.model';

export interface IAttribute {
  id?: number;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  value?: IAttributePossibleValue | null;
  key?: IAttributeKey | null;
  lead?: ILead | null;
  campaign?: ICampaign | null;
}

export const defaultValue: Readonly<IAttribute> = {
  isActive: false,
};
