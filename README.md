# Salesforce ALM Framework - CI/CD Pipelines for Metadata API

Groovy pipelines for Jenkins to perform ALM (Application Lifecycle Management) operations over Salesforce platform using Metadata API. The DX version of these pipelines can be found in [ALM-SF-DX-Pipelines](https://github.com/Accenture/ALM-SF-DX-Pipelines).

These pipelines are meant to be used altogether with Python scripts from [LM-SF-Metadata-API-Python-Tools](https://github.com/Accenture/ALM-SF-Metadata-API-Python-Tools).

Currently supporting:
- Gitlab CE
- Gitlab EE
- Gitlab.com
- Bitbucket Cloud 
- Bitbucket Server

## Git2SF

Automatic pipeline triggered from a Merge/Pull Request creation and/or modification. The job performs a validation/deployment of the differences exisiting between the source and target branches in the configured org. 

Detailed explanation and job configuration can be found at [Git2SF README](/docs/Git2SF_README.md).

## DeployerOpenMR

Handles the manually executed jobs to validate/deploy metadata into Salesforce using as input parameters the Merge/Pull Request Id and the target Salesforce environment.

Detailed explanation and job configuration can be found at [DeployerOpenMR README](/docs/DeployerOpenMR_README.md).

## Deployer

Handles the manually executed jobs to validate/deploy metadata into Salesforce using as input parameters the source and target commit hashes (or tag names) and the target Salesforce environment.

Detailed explanation and job configuration can be found at [Deployer README](/docs/Deployer_README.md).

## Pipeline Libraries

Set of shared libraries wich are used by the pipelines execution.

Detailed explanation of each library can be found at [Libraries README](/var/README.md).

Detailed explanation and job configuration can be found at [Deployer README](/docs/Deployer_README.md).


# Contributors Guide

## Contribution

We encourage you to contribute to new features or fixes. You can start by opening a
[Github issue](https://github.com/Accenture/ALM-SF-DX-Pipelines/issues) to get feedback from other contributors.

## License

The Salesforce ALM Framework is licensed under the Apache License 2.0 - see [LICENSE](LICENSE) for details.
