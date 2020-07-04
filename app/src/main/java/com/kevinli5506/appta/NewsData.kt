package com.kevinli5506.appta

import com.kevinli5506.appta.Model.News
import java.util.*
import kotlin.collections.ArrayList

object NewsData {
    private val newsTitle ="Sungai Menjijikkan Penuh Sampah di Pasuruan Dikeruk "
    private val newsTitle2 = "Dampak Covid-19, Sampah APD Mengapung di Laut Mediterania "
    private val newsRating = 4f
    private val newsDescription = "Jawa Timur - Sungai menjijikkan penuh sampah di Desa Tambak Lekok, Pasuruan akhirnya dikeruk. Sungai tersebut merupakan tempat pembuangan sampah warga sekitar. Sungai penuh sampah yang dikeruk ini berasa di Desa Tambak Lekok, Kecamatan Lekok, Kabupaten Pasuruan. Pengerukan menggunakan satu alat berat dan tiga truk. Kepala Dinas Lingkungan Hidup (DLH) Kabupaten Pasuruan, Heru Farianto mengatakan, pengerukan membutuhkan waktu seminggu.\n" +
            "Pengerukan sampah dimulai sejak pagi. Sejumlah warga tampak antusias menyaksikan pengerukan sampah di sungai tersebut. Sampah yang memenuhi sungai dikeruk alat berat kemudian diangkut menggunakan truk. Saat ini kondisi sungai sangat kotor. Airnya juga berwarna hitam dan menimbulkan bau. Kondisi sungai Desa Tambak Lekok ini sangat menjijikan karena menjadi tempat pembuangan sampah. Kebiasaan membuang sampah ke sungai ini telah dilakukan warga selama bertahun-tahun karena tidak ada tempat pembuangan sampah (TPS).\n" +
            "Sumber: https://news.detik.com/foto-news/d-5067953/sungai-menjijikkan-penuh-sampah-di-pasuruan-dikeruk/8\n"
    private val newsDescription2 = "KOMPAS.com - Banyak negara melakukan karantina mandiri dan lockdown sebagai upaya menghadapi pandemi Covid-19. Hal ini berpengaruh terhadap penurunan emisi karbon dioksida harian secara global, meski bersifat sementara. \n" +
            "Di sisi lain, pandemi Covid-19 juga membawa dampak buruk bagi lingkungan. Sarung tangan lateks dan masker wajah banyak ditemukan di pantai dan selokan sejumlah negara. Sejumlah organisasi yang peduli pada lingkungan telah menyuarakan keprihatinan terkait hal ini. Mereka mengatakan, laut, sungai, dan selokan dibanjiri oleh masker bekas sekali pakai, sarung tangan lateks, botol hand sanitizer, dan barang-barang alat pelindung diri ( APD) yang tidak dapat didaur ulang.\n" +
            "Kelompok konservasi laut Perancis, Opération Mer Propre yang secara teratur mendokumentasikan aksi bersih-bersih laut di media sosial juga melaporkan telah melihat banyak APD di Laut Mediterania. \"Limbah baru yang terkait dengan Covid-19 sangat mengkhawatirkan. Kami menemukan banyak sampah seperti ini saat melakukan pembersihan pantai, terutama sarung tangan lateks,\" ungkap Opération Mer Propre dalam postingan Facebook mereka pada 20 Mei 2020. \"Kemudian kami juga menemukan banyak masker sekali pakai di Laut Mediterania. Ini hanya permulaan. Jika tidak ada perubahan, hal ini akan menjadi bencana ekologis nyata dan mungkin memengaruhi kesehatan,\" tulis kelompok itu pada 23 Mei 2020.\n" +
            "Sejumlah pejabat kota di AS juga melaporkan bahwa selokan dan stasiun pompa air hujan tersumbat oleh sarung tangan lateks dan masker. Meski belum ada data terkait skala masalah, media Associated Press telah menghubungi 15 otoritas kota di AS. Semuanya melaporkan bahwa banyak saluran pembuangan yang tersumbat dan masalah drainase sejak pandemi dimulai. \n" +
            "Dilansir IFL Science, Selasa (9/6/2020), sehubungan dengan masalah pencemaran lingkungan ini, Badan Perlindungan Lingkungan (EPA) AS mengeluarkan pernyataan yang mengimbau masyarakat untuk membuang APD Covid-19 dnegan benar. Panduan ini termasuk, tidak membuang tisu disinfektan, sarung tangan, masker, APD, dan limbah medis apapun ke tempat sampah daur ulang. Ini karena limbah tersebut dapat terkontaminasi oleh patogen dan dianggap membahayakan kesehatan. Sejumlah organisasi daur ulang pun mendesar semua orang untuk membuang masker dan sarung tangan dengan aman. Caranya dengan membuangnya ke keranjang sampah umum. \"Semua orang dilarang meninggalkan sarung tangan dan plastik di tempat parkir atau melemparkannya ke semak-semak,\" kata David Biderman, direktur eksekutif dan CEO Asosiasi Limbah Padat Amerika Utara (SWANA) dalam sebuah pernyataan. \"APD bekas yang dibuang ke tanah dapat meningkatkan risiko paparan Covid-19 dan berdampak negatif untuk lingkungan,\" imbuhnya.\n"
    private val newsDate1:Calendar
    get() {
        val time = Calendar.getInstance()
        time.set(2020,3,10,17,20,13)
        return time
    }
    private val newsDate2:Calendar
        get() {
            val time = Calendar.getInstance()
            time.set(2020,3,4,17,20,13)
            return time
        }
    private val newsImage:Int = R.drawable.newsexample
    private val newsImage2:Int = R.drawable.newsexample2

    val listNews :ArrayList<News>
    get(){
        val arr = arrayListOf<News>()
        for (i in 1..5){
            val news = News()
            news.title= if(i%2==0) newsTitle else newsTitle2
            news.rating= newsRating
            news.time = if(i%2==0) newsDate1 else newsDate2
            news.image =if(i%2==0) newsImage else newsImage2
            news.description= if(i%2==0) newsDescription else newsDescription2
            arr.add(news)
        }
        return arr
    }
}