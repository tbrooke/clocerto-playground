# Clocerto Library Files 

This directory contains a sample library of Concerto files and Cicero templates. 

Documentation for using the Accord Project Tools can be found at [Documentation](https://docs.accordproject.org/versions.html).

The Sample contracts are all from the [Accord Project Library](https://templates.accordproject.org/). They can be edited in VS Code with the Accord Project [VS Code Extension](https://github.com/accordproject/vscode-extension) that can be installed from the Vs Code Market place.

 There is a tutorial at [VS Code Tutorial](https://docs.accordproject.org/docs/next/tutorial-vscode.html).

 ## Library 

These contract templates are  from the [Accord Project Template Library](https://templates.accordproject.org/) that stores them as an archive (.cta) file. Cicero archives are files with a .cta extension, which includes all the different components for the template (text, model and logic).

To work with the templates from the command line you need to install the latest version of the Cicero command-line tools:

```
npm install -g @accordproject/cicero-cli
```

To work with Concerto models from the command line you need to install the Concerto CLI

Open a shell and run the command:

```
npm install -g @accordproject/concerto-cli
```

The library directory has subdirectories for the models that contain all the models used with the templates and the and an AST subdirectory that contains ast.json version of all the models

Each template below has a subdirectory with the CTA file and expanded version of the entire archive as well as an expanded version of the archive including Cicero/Markdown in cicero subdirectory for each template


- ### [Hello World](https://templates.accordproject.org/helloworld@0.14.0.html)

- ### [Late Delivery and Penalty](https://templates.accordproject.org/latedeliveryandpenalty-optional-this@0.2.0.html)

- ### [Interest Rate Swap](https://templates.accordproject.org/interest-rate-swap@0.8.0.html)

    [Edgar reference for Interest Rate Swap](https://www.sec.gov/Archives/edgar/data/1325702/000119312505180322/dex1035.htm)



