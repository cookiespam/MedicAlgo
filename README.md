# Voice Activated App for Medical Algorithms

### Voice Recognition Setup

Enabling voice recognition requires two parts, [Google Cloud Speech to Text](https://cloud.google.com/speech-to-text) or [Amazon Transcribe](https://aws.amazon.com/transcribe/) for voice recognition and [LUIS.ai](https://www.luis.ai/) for Natural Language Processing.

##### Google Cloud Speech to Text

Requires API key obtained from Google Cloud, to be entered in the app itself.

##### Amazon Transcribe 

Requires access key and API key from AWS, to be entered in the app itself. Recommended to include a [custom vocabulary](https://aws.amazon.com/blogs/machine-learning/build-a-custom-vocabulary-to-enhance-speech-to-text-transcription-accuracy-with-amazon-transcribe/) to enhance recognition of specific keywords such as laryngectomy.

##### LUIS.ai

The ```MedicAlgo.json``` file available in the root of the repo describes and needs to [imported](https://docs.microsoft.com/en-us/azure/cognitive-services/luis/luis-how-to-manage-versions) into a LUIS.ai app. Requires appId, key and endpoint to be filled in ```src/main/java/com/mbaxajl3/medicalgo/controllers/NLUController.java```





### Tests

Testing is split into two parts, local and instrumented unit tests. The latter automates UI testing and forms the main bulk of unit testing.

To generate code coverage of instrumented unit tests in androidTest, run ```.\gradlew createDebugCoverageReport```

Will take >10mins to run, and may fail sometimes.