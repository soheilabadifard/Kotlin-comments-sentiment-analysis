# Kotlin Comments Sentiment Analysis #

![Build](https://github.com/soheilabadifard/Kotlin-comments-sentiment-analysis/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)



## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Installation](#installation)
- [Requirements](#requirements)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)



---
## Overview
<!-- Plugin description -->
This IntelliJ IDEA plugin performs sentiment analysis on comments within Kotlin files using the [RoBERTa](https://github.com/onnx/models/tree/main/validated/text/machine_comprehension/roberta) model. It can be accessed via an Tools menu action with name of **Analyze KT File's Sentiment**, providing statistics per file for the analyzed sentiments.
<!-- Plugin description end -->

---
## Features
- Implemented in Kotlin.
- Built using the [IntelliJ Platform Plugin Template][template].
- Utilizes the [KInference](https://github.com/JetBrains-Research/kinference) library for model inference.
- Uses the RoBERTa Sequence Classification model for sentiment analysis.
- [RoBERTa tokenizer](https://github.com/purecloudlabs/roberta-tokenizer/tree/main) implemented in Java is used.
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
- Make sure that the [RoBERTa-SequenceClassification](https://github.com/onnx/models/tree/main/validated/text/machine_comprehension/roberta) model is downloaded and placed in the `root` folder of the project that the plugin will operate on beforehand. If the model is not found, the plugin will download it automatically. The size of the model is 0.5 GB.
- The plugin will only work on Kotlin Projects and analyze the files with the `.kt` extension.
- In order to run the plugin 3 Important files also need to be present in the `root` folder of the project:
    - `base_vocabulary.json` - map of numbers ([0,255]) to symbols (UniCode Characters). Only those symbols will be known by the algorithm. e.g., given s as input it iterates over the bytes of the String s and replaces each given byte with the mapped symbol. This way we assure what characters are passed.
    - `merges.txt` - describes the merge rules of words. The algorithm splits the given word into two subwords, afterward it decides the best split according to the rank of the sub words. The higher those words are, the higher the rank.
    - `vocabulary.json` - Is a file that holds all the words(sub-words) and their token according to training.
    -  The plugin will automatically place them in the `root` folder of the project if they are not found. However, if in case the plugin is not able to place them, you can place them manually from [here](./src/main/resources/base_vocabulary.json) or [here](./src/test/testdata).


---

## Usage
- After [installing](#installation) the plugin, and before opening a project, make sure that the [requirements](#requirements) are met.
- Open a Kotlin project in IntelliJ IDEA.
- Go to <kbd>Tools</kbd> > <kbd>Analyze KT File's Sentiment</kbd> .
- A side panel on the right side will be ready with the statistics of the analyzed sentiments for each Kotlin file.

### Please note:
- The results will be shown after all the background activities such as indexing are finished.

---
## Contributing
Contributions to the plugin are welcome. Please submit pull requests to the repository for any enhancements.

---
## License
Apache 2.0

---

[template]: https://github.com/JetBrains/intellij-platform-plugin-template

