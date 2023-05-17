import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Chauffeur = () => import('@/entities/chauffeur/chauffeur.vue');
// prettier-ignore
const ChauffeurUpdate = () => import('@/entities/chauffeur/chauffeur-update.vue');
// prettier-ignore
const ChauffeurDetails = () => import('@/entities/chauffeur/chauffeur-details.vue');
// prettier-ignore
const Client = () => import('@/entities/client/client.vue');
// prettier-ignore
const ClientUpdate = () => import('@/entities/client/client-update.vue');
// prettier-ignore
const ClientDetails = () => import('@/entities/client/client-details.vue');
// prettier-ignore
const Admin = () => import('@/entities/admin/admin.vue');
// prettier-ignore
const AdminUpdate = () => import('@/entities/admin/admin-update.vue');
// prettier-ignore
const AdminDetails = () => import('@/entities/admin/admin-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'chauffeur',
      name: 'Chauffeur',
      component: Chauffeur,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'chauffeur/new',
      name: 'ChauffeurCreate',
      component: ChauffeurUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'chauffeur/:chauffeurId/edit',
      name: 'ChauffeurEdit',
      component: ChauffeurUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'chauffeur/:chauffeurId/view',
      name: 'ChauffeurView',
      component: ChauffeurDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client',
      name: 'Client',
      component: Client,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/new',
      name: 'ClientCreate',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/edit',
      name: 'ClientEdit',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/view',
      name: 'ClientView',
      component: ClientDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'admin',
      name: 'Admin',
      component: Admin,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'admin/new',
      name: 'AdminCreate',
      component: AdminUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'admin/:adminId/edit',
      name: 'AdminEdit',
      component: AdminUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'admin/:adminId/view',
      name: 'AdminView',
      component: AdminDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
