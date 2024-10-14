# Document Scanner App
![App Sceenshot](https://i.imgur.com/ATl8egX.png)

## Overview

This Android app is a modern document scanner that leverages the [Jetpack Compose](https://developer.android.com/compose) library as well as [Google ML Kit](https://developers.google.com/ml-kit) and follows the MVVM architecture. The app features automated document title suggestions and bill amount extraction using the following Google ML Kit APIs:
- [Document Scanner](https://developers.google.com/ml-kit/vision/doc-scanner)
- [Text Recognition V2](https://developers.google.com/ml-kit/vision/text-recognition/v2)
- [Entity Extraction](https://developers.google.com/ml-kit/language/entity-extraction)


## Features

- **Document Scanning**: Capture high-quality scans of documents.
- **Title Suggestions**: Automatically suggest titles for scanned documents.
- **Bill Amount Extraction**: Use OCR to extract amounts from bills.
- **Modern UI**: Built with Jetpack Compose for a responsive and user-friendly experience.

## Technologies Used

- **Android SDK**
- **Jetpack Compose**
- **Dagger Hilt**
- **Google ML Kit** (for OCR and document scanning)
- **MVVM Architecture**

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/YungSpecht/Scanner.git
   cd Scanner
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device.

## Usage

1. Launch the app.
2. Tap the Floating Action Button to start a new Scan process.
3. The app will automatically suggest a title and bill amount for the document.
4. Edit them if needed and save the document.
5. To edit or delete a document tap on the document from within the main screen.

## Limitations
- Only one page documents can currently be scanned
- No advanced Document Layout Understanding model is used to analyze document
- The highest amount will be extracted as the bill amount


Device Mockup created from [Google Pixel 6 mockups](https://deviceframes.com/templates/google-pixel-6)
