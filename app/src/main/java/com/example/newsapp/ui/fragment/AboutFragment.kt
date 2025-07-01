package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentAboutBinding.bind(view)

        binding.tvAppName.text = "News App"
        binding.tvDescription.text = """
            News App adalah aplikasi berita yang memudahkan pengguna mendapatkan informasi terbaru dari seluruh dunia. Dengan tampilan sederhana dan mudah digunakan, pengguna bisa membaca berita populer seperti politik, teknologi, hiburan, dan olahraga, serta menyimpan berita favorit ke Bookmark atau Favourite untuk diakses kapan saja.
           
            Fitur Unggulan NewsApp:
            ✔️ Update berita real-time dari berbagai sumber   
            ✔️ Bookmark berita untuk dibaca nanti  
            ✔️ Tandai berita favorit Anda  
            ✔️ Desain sederhana, ringan, dan mudah digunakan  
            ✔️ Gambar thumbnail untuk setiap berita  
            ✔️ Informasi singkat yang mudah dipahami  

            Aplikasi ini hadir untuk membantu Anda tetap terhubung dengan perkembangan dunia tanpa harus repot membuka banyak situs berita. Dapatkan pengalaman membaca berita yang cepat, ringan, dan menyenangkan!
        """.trimIndent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
