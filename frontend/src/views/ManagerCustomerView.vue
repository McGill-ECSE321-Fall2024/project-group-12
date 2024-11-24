<!--
 Manager Customer page
 @author Carmin Vidé
-->
<script setup>
import { ref } from 'vue';
import { inject } from 'vue'
import listItem from '@/components/UserListItem.vue'
const { createThemeFromColour } = inject('theme')
// change to a red theme to match the holiday effect
createThemeFromColour('#FF9797')

const userId = ref("89023794");
const userName = ref("Cunégonde Theodraalia");
const userEmail = ref("cune@hotmail.dk");

const customers = ref([
  { id: "89023794", name: "Cunégonde Theodraalia", email: "laplusbellecune@hotmail.dk" },
  { id: "12345678", name: "John Doe", email: "john.doe@example.com" },
  { id: "87654321", name: "Jane Smith", email: "jane.smith@example.com" },
  { id: "13579246", name: "Antoine Dupont", email: "antoine.dupont@example.fr" },
  { id: "24681357", name: "María García", email: "maria.garcia@example.es" },
  { id: "87390", name: "María García", email: "maria.garcia@example.es" },
  { id: "24681357", name: "María García", email: "maria.garcia@example.es" },
  { id: "24681357", name: "María García", email: "maria.garcia@example.es" }
])

</script>

<template>
  <main>
    <div class="title">
      <h2>Customers</h2>
    </div>
    <div>
        <listItem 
        v-for="customer in customers" 
        :key="customer.id"
        :id="customer.id"
        :name="customer.name"
        :email="customer.email"
        />
    </div>
    
  </main>
</template>

<style scoped>
h1 {
  color: #ffffff;
  text-align: center;
  font-size: 36px;
}
.title {
  display: flex;
  flex-direction: column
  /* margin-top: 20px;
    margin-bottom: 20px; */
}
.icon {
  width: 48px;
  height: 48px;
}

</style>