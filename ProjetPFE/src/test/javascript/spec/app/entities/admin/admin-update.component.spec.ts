/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AdminUpdateComponent from '@/entities/admin/admin-update.vue';
import AdminClass from '@/entities/admin/admin-update.component';
import AdminService from '@/entities/admin/admin.service';

import ChauffeurService from '@/entities/chauffeur/chauffeur.service';

import ClientService from '@/entities/client/client.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Admin Management Update Component', () => {
    let wrapper: Wrapper<AdminClass>;
    let comp: AdminClass;
    let adminServiceStub: SinonStubbedInstance<AdminService>;

    beforeEach(() => {
      adminServiceStub = sinon.createStubInstance<AdminService>(AdminService);

      wrapper = shallowMount<AdminClass>(AdminUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          adminService: () => adminServiceStub,
          alertService: () => new AlertService(),

          chauffeurService: () =>
            sinon.createStubInstance<ChauffeurService>(ChauffeurService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          clientService: () =>
            sinon.createStubInstance<ClientService>(ClientService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 'ABC' };
        comp.admin = entity;
        adminServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adminServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.admin = entity;
        adminServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adminServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAdmin = { id: 'ABC' };
        adminServiceStub.find.resolves(foundAdmin);
        adminServiceStub.retrieve.resolves([foundAdmin]);

        // WHEN
        comp.beforeRouteEnter({ params: { adminId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.admin).toBe(foundAdmin);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
