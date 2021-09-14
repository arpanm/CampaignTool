import dayjs from 'dayjs';
import { ICampaign } from 'app/shared/model/campaign.model';
import { CompanyType } from 'app/shared/model/enumerations/company-type.model';

export interface IClient {
  id?: number;
  companyName?: string | null;
  companyWebsite?: string | null;
  companyType?: CompanyType | null;
  primaryPhone?: string | null;
  description?: string | null;
  isActive?: boolean | null;
  createdBy?: string | null;
  createdAt?: string | null;
  updatedBy?: string | null;
  updatedAt?: string | null;
  campaigns?: ICampaign[] | null;
}

export const defaultValue: Readonly<IClient> = {
  isActive: false,
};
