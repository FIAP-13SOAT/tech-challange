terraform {
    required_providers {
        aws = {
            source  = "hashicorp/aws"
            version = "6.17.0"
        }
    }
}

locals {
    projectName = "garage"
    awsRegion = "us-east-1"
}

provider "aws" {
    region = local.awsRegion
}

