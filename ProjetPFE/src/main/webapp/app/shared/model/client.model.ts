import { IAdmin } from '@/shared/model/admin.model';

export interface IClient {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  admin?: IAdmin | null;
}

export class Client implements IClient {
  constructor(
    public id?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string | null,
    public phoneNumber?: string | null,
    public admin?: IAdmin | null
  ) {}
}
