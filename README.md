# E-Shopping Platform

## Introduction
This repository contains the source code and documentation for the E-Shopping Platform, a multi-tenant SaaS solution designed to facilitate online shopping and management for various products.

## Features
- Product catalog with extensive search capabilities.
- Shopping cart functionality.
- Multiple payment methods support.
- Admin interface for product and order management.
- Event-sourcing based architecture for robust operation.

## Domain Definitions
The platform is structured around several key domain concepts:
- **Catalog**: Information about products and services.
- **Product**: Items listed under the catalog for sale.
- **Cart**: Where users add products to purchase.

![Domain Diagram](images/eshopping_domain_diagram.jpg)

## Workflow
The workflow for both users and admins are as follows:

### User Workflow
1. Search and select products.
2. Add products to the cart.
3. Proceed to checkout and payment.

![User Workflow](images/eshopping_workflow_diagram.jpg)

### Admin Workflow
1. Manage catalog and products.
2. View and handle orders.
3. Generate reports.

## Architecture
The system employs a microservices architecture to ensure scalability and reliability. Below is the component diagram illustrating the primary services and their interactions.

![Component Diagram](images/Eshopping_component_diagram.jpg)

## Deployment Design
![Container Diagram](images/Eshopping_container_diagram.jpg)

## Low-Level Design
Please refer to the low-level design document for detailed API endpoints, database schema, and sequence diagrams that describe the interactions between various components.

![DB Schema](images/eshopping_schema.png)
![Sequence Diagram](images/sequence_diagram.jpeg)

## Installation
Provide step-by-step series of examples and explanations about how to get a development environment running. Mention prerequisites, environment setup, and deployment steps.

```bash
# Clone the repository
git clone https://example.com/eshopping-platform.git

# Navigate to the repository directory
cd eshopping-platform
