import { IAdmin } from '@/shared/model/admin.model';

export interface IChauffeur {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  admin?: IAdmin | null;
}

export class Chauffeur implements IChauffeur {
  constructor(
    public id?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string | null,
    public phoneNumber?: string | null,
    public admin?: IAdmin | null
  ) {}
}
