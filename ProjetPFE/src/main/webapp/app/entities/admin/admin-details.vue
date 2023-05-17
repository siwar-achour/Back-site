<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="admin">
        <h2 class="jh-entity-heading" data-cy="adminDetailsHeading">
          <span v-text="$t('projetPfeApp.admin.detail.title')">Admin</span> {{ admin.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('projetPfeApp.admin.roleName')">Role Name</span>
          </dt>
          <dd>
            <span>{{ admin.roleName }}</span>
          </dd>
          <dt>
            <span v-text="$t('projetPfeApp.admin.userName')">User Name</span>
          </dt>
          <dd>
            <span>{{ admin.userName }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="admin.id" :to="{ name: 'AdminEdit', params: { adminId: admin.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./admin-details.component.ts"></script>
