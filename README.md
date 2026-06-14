# Tugas IR - Model Probabilistik

Project ini dibuat untuk tugas Information Retrieval tentang implementasi model probabilistik. Program membangun inverted index dari kumpulan dokumen, menjalankan beberapa model retrieval, dan mengevaluasi hasil ranking menggunakan query serta relevance judgment.

## Anggota Kelompok

- Tobias Abigail Budiman
- Frederick Maximillan Tanupribadi

## Dataset

Dataset yang digunakan berasal dari Cranfield collection dalam bentuk subset:

- `data/documents.txt`: 200 dokumen bahasa Inggris.
- `data/queries.txt`: 10 query.
- `data/qrels.txt`: relevance judgment untuk evaluasi.

Format file:

```text
documents.txt : documentId|title|content
queries.txt   : queryId|query text
qrels.txt     : queryId|documentId|relevance
```

Nilai relevance yang lebih besar dari 0 dianggap relevan.

## Struktur Project

```text
src/ir/Main.java                 titik masuk program
src/ir/app/                      alur utama aplikasi
src/ir/document/                 class Document
src/ir/query/                    class Query
src/ir/preprocessing/            tokenizer, stopword remover, stemmer
src/ir/index/                    inverted index, posting, indexer
src/ir/retrieval/                search engine, factory, model type
src/ir/retrieval/model/          implementasi BIM, Two-Poisson, BM25, BM10
src/ir/evaluation/               evaluator, relevance judgment, hasil evaluasi
src/ir/io/                       reader, console input, result writer
data/                            dokumen, query, dan qrels
```

## Preprocessing

Tahapan preprocessing yang digunakan:

1. Lowercase.
2. Tokenization.
3. Menghapus tanda baca dan karakter non-alphanumeric.
4. Stopword removal.
5. Simple stemming.

Hasil preprocessing digunakan untuk membangun inverted index dan memproses query.

## Inverted Index

Inverted index menyimpan:

- term.
- posting list.
- documentId.
- term frequency.
- document length.
- average document length.
- document frequency.

Informasi ini dipakai oleh model probabilistik untuk menghitung skor dokumen.

## Model Retrieval

### BIM

BIM menggunakan representasi binary, yaitu hanya melihat apakah term muncul atau tidak muncul dalam dokumen.

Rumus bobot sederhana:

```text
w_t = log((0.5 * N) / df_t)
```

Skor dokumen:

```text
score(d, q) = sum w_t
```

untuk setiap term query yang muncul pada dokumen.

### Two-Poisson

Implementasi Two-Poisson pada project ini menggunakan pendekatan tf saturation.

Rumus skor term:

```text
score(t, d) = (tf * (k + 1) * w_t) / (tf + k)
```

dengan:

```text
w_t = log((0.5 * N) / df_t)
```

Parameter:

```text
k default = 1.0
```

Jika user tidak mengisi nilai `k`, program memakai default. Jika user memasukkan nilai tidak valid, program juga kembali memakai default.

### BM25

BM25 menggunakan term frequency, inverse document frequency, dan normalisasi panjang dokumen.

Rumus IDF:

```text
idf = log(((N - df + 0.5) / (df + 0.5)) + 1)
```

Rumus skor term:

```text
score(t, d) = idf * (tf * (k1 + 1)) / (tf + k1 * (1 - b + b * (dl / avgdl)))
```

Parameter:

```text
k1 default = 1.5
b  default = 0.75
```

Jika user tidak mengisi parameter, program memakai default. Nilai `k1` harus lebih besar dari 0, sedangkan `b` harus berada di antara 0 dan 1.

### BM10

BM10 mirip dengan BM25, tetapi tidak menggunakan normalisasi panjang dokumen.

Rumus skor term:

```text
score(t, d) = idf * (tf * (k1 + 1)) / (tf + k1)
```

Parameter default:

```text
k1 = 1.5
```

## Design Pattern

Project ini menggunakan Factory Pattern pada `RetrievalModelFactory`.

Tujuannya adalah supaya pembuatan model retrieval tidak dilakukan langsung di banyak tempat. `IRApplication` cukup memilih `ModelType`, lalu factory akan membuat object model yang sesuai.

Contoh model yang dibuat oleh factory:

- `BIMModel`
- `TwoPoissonModel`
- `BM25Model`
- `BM10Model`

## Mode Program

Saat program dijalankan, user dapat memilih:

```text
1. Input query sendiri
2. Input query dari file queries.txt
```

### Mode 1 - Input Query Sendiri

User memasukkan query secara manual, memilih model retrieval, menentukan TopK, dan program menampilkan ranking dokumen.

### Mode 2 - Input Query dari File

Program membaca semua query dari `queries.txt`, membaca qrels dari `qrels.txt`, lalu user memilih model retrieval dan TopK untuk evaluasi.

Setelah evaluasi selesai, user dapat memilih:

```text
1. Evaluasi model lain
2. Selesai
```

## Evaluasi

Metrik evaluasi yang digunakan:

- Precision@K
- Recall@K
- F1@K
- Average Precision
- MAP
- 11-Point Average Precision

Precision, recall, F1, dan AP dihitung berdasarkan TopK yang dipilih user. Untuk 11-point average precision, ranking penuh digunakan agar perhitungan recall dari 0.0 sampai 1.0 dapat dilakukan.

## Cara Menjalankan Program

Compile semua source:

```bash
javac -d out @sources.txt
```

Jalankan program:

```bash
java -cp out ir.Main
```

Atau jalankan `ir.Main` langsung melalui IDE seperti IntelliJ IDEA.

## Contoh Alur Program

```text
Pilih mode program:
1. Input query sendiri
2. Input query dari file queries.txt

Pilih model retrieval:
1. BIM
2. Two-Poisson
3. BM25
4. BM10

TopK:
10
```

Jika memilih BM25, program akan meminta:

```text
k1 BM25
b BM25
```

Jika memilih Two-Poisson, program akan meminta:

```text
k Two-Poisson
```

Input parameter dapat dikosongkan agar program memakai nilai default.

## Catatan Implementasi

- Program tidak menggunakan library eksternal untuk retrieval model.
- Inverted index dibangun dari hasil preprocessing dokumen.
- Query diproses menggunakan preprocessing yang sama dengan dokumen.
- Ranking diurutkan berdasarkan skor tertinggi.
- Jika skor sama, ranking distabilkan menggunakan `documentId`.
- Hasil evaluasi ditampilkan di terminal, bukan disimpan ke file CSV.
