<template>
    <div>
        <h1>Kullanıcılar</h1>
        <ul>
            <li v-for="kullanici in kullanicilar" :key="kullanici.id">
                <h2>{{ kullanici.ad }} {{ kullanici.soyad }}</h2>
                <p>Yaş: {{ kullanici.yas }}</p>
            </li>
        </ul>
    </div>
</template>
  
<script>
export default {
    data() {
        return {
            kullanicilar: []
        };
    },
    mounted() {
        this.fetchKullanicilar();
    },
    methods: {
        fetchKullanicilar() {
            //"https://github.com/Ebu13/DjangoProjects/tree/main/api_project" 
            //was included in the project with the API in the Django project at this address.
            fetch("http://localhost:8000/api/kullanicilar/")
                .then(response => response.json())
                .then(data => {
                    this.kullanicilar = data.kullanicilar;
                })
                .catch(error => {
                    console.error("API'den veri alınamadı:", error);
                });
        }
    }
};
</script>
  
<style scoped>
/* Stil tanımlamalarını buraya ekleyebilirsiniz */
</style>
  