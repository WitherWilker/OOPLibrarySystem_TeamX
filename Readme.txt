Kelompok Terdiri dari :
1. Muhammad Idris Dwi Saputra 
(202410370110018)
2. Muhammad Arif Rakhman
(202410370110033)
3. Abdul Halim 
(202410370110016)

Link GitHub : https://github.com/WitherWilker/OOPLibrarySystem_TeamX.git

UMM Library 

Campus Library System adalah aplikasi Sistem Informasi Perpustakaan Kampus yang dikembangkan menggunakan bahasa pemrograman Java dengan antarmuka grafis berbasis JavaFX. Aplikasi ini dibuat sebagai bagian dari Tugas Akhir Praktikum Pemrograman Berorientasi Objek (PBO) dengan tujuan membantu mahasiswa maupun petugas perpustakaan dalam mengelola peminjaman dan pengembalian buku secara lebih praktis, cepat, dan efisien.

Aplikasi ini mendukung berbagai fungsi penting yang umum digunakan di lingkungan perpustakaan kampus. Pengguna dapat melakukan pencarian buku dengan mudah melalui fitur search yang mendukung filter berdasarkan judul, ISBN, penulis, atau genre buku. Selain itu, mahasiswa dapat melakukan peminjaman buku secara mandiri dengan hanya memasukkan ISBN, dan sistem akan secara otomatis mencatat tanggal peminjaman serta menentukan tanggal pengembalian sesuai aturan yang berlaku (misalnya maksimal 7 hari). Apabila buku dikembalikan melewati batas waktu, sistem secara otomatis menghitung jumlah denda yang harus dibayarkan sesuai dengan jumlah hari keterlambatan.

Struktur proyek ini dirancang dengan pola berbasis package yang rapi dan terpisah sesuai tanggung jawabnya. Pada paket model, terdapat kelas Book, Member, dan Transaction yang masing-masing bertugas merepresentasikan data buku, data anggota perpustakaan, serta data transaksi peminjaman dan pengembalian. Paket repository bertugas sebagai penghubung antara data dan logika bisnis, menangani penyimpanan data buku maupun transaksi secara sederhana (belum terhubung dengan database eksternal, tetapi sudah dirancang agar mudah dikembangkan di masa depan).

Paket service merupakan inti logika bisnis aplikasi. Di dalamnya terdapat kelas BookService dan TransactionService yang mengatur validasi status buku (tersedia atau dipinjam), pembuatan transaksi baru, penanganan pengembalian, serta penentuan denda. Proses penghitungan denda juga dibantu oleh kelas FineCalculator yang terdapat pada paket util, bersama dengan DateUtil untuk perhitungan selisih tanggal secara akurat. Untuk mendukung pengalaman pengguna, tersedia juga CustomAlert pada paket util, yang menampilkan notifikasi popup menggunakan komponen Alert dari JavaFX dengan pesan yang lebih jelas dan informatif.

Bagian tampilan antarmuka atau front-end dikerjakan secara khusus oleh Idris. Idris bertanggung jawab mendesain dan mengatur seluruh layout scene JavaFX, mulai dari login panel, library panel, tampilan tabel buku, hingga form input untuk pinjam dan pengembalian buku. Komponen-komponen seperti tombol Search, Borrow, Return, hingga layout HBox dan VBox disusun agar mudah digunakan dan responsif, sehingga memudahkan mahasiswa dalam berinteraksi dengan aplikasi.

Sementara itu, bagian back-end sepenuhnya dikerjakan oleh Arif. Arif mengimplementasikan logika peminjaman dan pengembalian, memastikan validasi data berjalan sesuai ketentuan, serta membuat skema session agar hanya pengguna yang login yang dapat melakukan transaksi. Selain itu, seluruh perhitungan denda dan pengelolaan status buku (tersedia atau dipinjam) juga ditangani di sisi back-end, sehingga data yang ditampilkan di tabel selalu real-time dan konsisten.

Secara keseluruhan, aplikasi ini dirancang untuk memberikan pengalaman menggunakan sistem perpustakaan digital yang sederhana namun fungsional. Mahasiswa dapat melakukan semua transaksi secara mandiri tanpa perlu berinteraksi langsung dengan petugas, dan di sisi lain, aplikasi ini dapat dikembangkan lebih lanjut untuk mendukung database relasional, fitur pelaporan, maupun notifikasi otomatis melalui email atau SMS.

Proyek ini membuktikan bagaimana konsep Pemrograman Berorientasi Objek (PBO) dapat diimplementasikan pada aplikasi nyata dengan pembagian tanggung jawab yang jelas, struktur package yang modular, serta pemisahan antara front-end dan back-end. Harapannya, aplikasi ini dapat dijadikan dasar untuk proyek pengembangan sistem informasi perpustakaan yang lebih lengkap di kemudian hari.