<template>
    <div>
        <div v-if='!profile'>Необходимо авторизоваться через <a href='/login'>Google</a></div>
        <div v-else>
            <div>{{profile.name}}&nbsp;<a href='/logout'>Выход</a></div>
        </div>
        <p>{{message}}</p>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                message: 'Привет, Vue!',
                profile: frontendData.profile
            }
        }
    }
</script>

<style>

</style>