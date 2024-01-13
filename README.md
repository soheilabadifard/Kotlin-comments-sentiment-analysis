# Kotlin-comments-sentiment-analysis

![Build](https://github.com/soheilabadifard/Kotlin-comments-sentiment-analysis/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)


## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Installation](#installation)
- [Requirements](#requirements)
- [Usage](#usage)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

<!-- Plugin description -->
This Fancy IntelliJ Platform Plugin is going to be your implementation of the brilliant ideas that you have.

This specific section is a source for the [plugin.xml](/src/main/resources/META-INF/plugin.xml) file which will be extracted by the [Gradle](/build.gradle.kts) during the build process.

To keep everything working, do not remove `<!-- ... -->` sections.
<!-- Plugin description end -->

---
## Overview

This IntelliJ IDEA plugin performs sentiment analysis on comments within Kotlin files using the [RoBERTa][RoBERTa-SequenceClassification] model. It can be accessed via an Tools menu action with name of **Analyze KT File's Sentiment**, providing statistics per file for the analyzed sentiments.


---
## Features
- Implemented in Kotlin.
- Built using the [IntelliJ Platform Plugin Template][template].
- Utilizes the [KInference](https://github.com/JetBrains-Research/kinference) library for model inference.
- Uses the [RoBERTa Sequence Classification][RoBERTa-SequenceClassification] model for sentiment analysis.
- [RoBERTa tokenizer][RoBERTa-Tokenizer] implemented in Java is used.
- Code covered with tests, including comment extraction and model inference.
- Compatible with IntelliJ IDEA version 2023.2.3 or higher.

---
## Installation
- Download the plugin from [this](./build/distributions) repository.
- In IntelliJ IDEA, go to <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>.
- Select the downloaded plugin file.
- Restart IntelliJ IDEA to activate the plugin.

---
## Requirements
- Make sure that the [RoBERTa-SequenceClassification][RoBERTa-SequenceClassification] model is downloaded and placed in the `root` folder of the project that the plugin will operate on beforehand. If the model is not found, the plugin will download it automatically. The size of the model is 0.5 GB.
- The plugin will only work on Kotlin Projects and analyze the files with the `.kt` extension.
- In order to run the plugin 3 Important files also need to be present in the `root` folder of the project:
  - `base_vocabulary.json` - map of numbers ([0,255]) to symbols (UniCode Characters). Only those symbols will be known by the algorithm. e.g., given s as input it iterates over the bytes of the String s and replaces each given byte with the mapped symbol. This way we assure what characters are passed.
  - `merges.txt` - describes the merge rules of words. The algorithm splits the given word into two subwords, afterward it decides the best split according to the rank of the sub words. The higher those words are, the higher the rank.
  - `vocabulary.json` - Is a file that holds all the words(sub-words) and their token according to training.
  -  The plugin will automatically place them in the `root` folder of the project if they are not found. However, if in case the plugin is not able to place them, you can place them manually from [here](./src/main/resources) or [here](./src/test/testdata).


---

## Usage
- After [installing](#installation) the plugin, and before opening a project, make sure that the [requirements](#requirements) are met.
- Open a Kotlin project in IntelliJ IDEA.
- Go to <kbd>Tools</kbd> > <kbd>Analyze KT File's Sentiment</kbd> .
- A side panel on the right side will be ready with the statistics of the analyzed sentiments for each Kotlin file.

### Please note:
- The results will be shown after all the background activities such as indexing are finished.

## Testing
In order to test the plugin, you can use the [test project](./test%20project) provided in the repository. To do so, follow the steps below:
- Download zip file in the following directory [test project](./test%20project) and unzip it.
- If you have not already downloaded the [RoBERTa-SequenceClassification][RoBERTa-SequenceClassification] model, download it and place it in the `root` folder of the test project. Or you can let the plugin download it automatically.
- Then open the project in IntelliJ IDEA.
- Let the project index and build.
- Go to <kbd>Tools</kbd> > <kbd>Analyze KT File's Sentiment</kbd> .
- A side panel on the right side will be ready with the statistics of the analyzed sentiments for each Kotlin file.

---
## Contributing
Contributions to the plugin are welcome. Please submit pull requests to the repository for any enhancements.

---
## License
Apache 2.0

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
[RoBERTa-SequenceClassification]: https://github.com/onnx/models/tree/main/validated/text/machine_comprehension/roberta
[RoBERTa-Tokenizer]: https://github.com/purecloudlabs/roberta-tokenizer/tree/main