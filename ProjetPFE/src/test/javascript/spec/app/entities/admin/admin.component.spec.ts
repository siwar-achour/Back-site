/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AdminComponent from '@/entities/admin/admin.vue';
import AdminClass from '@/entities/admin/admin.component';
import AdminService from '@/entities/admin/admin.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Admin Management Component', () => {
    let wrapper: Wrapper<AdminClass>;
    let comp: AdminClass;
    let adminServiceStub: SinonStubbedInstance<AdminService>;

    beforeEach(() => {
      adminServiceStub = sinon.createStubInstance<AdminService>(AdminService);
      adminServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AdminClass>(AdminComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          adminService: () => adminServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      adminServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllAdmins();
      await comp.$nextTick();

      // THEN
      expect(adminServiceStub.retrieve.called).toBeTruthy();
      expect(comp.admins[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      adminServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(adminServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAdmin();
      await comp.$nextTick();

      // THEN
      expect(adminServiceStub.delete.called).toBeTruthy();
      expect(adminServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
