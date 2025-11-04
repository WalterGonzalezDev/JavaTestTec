# Arquitectura y Diagramas Técnicos

## Arquitectura general del sistema

```mermaid
flowchart LR
  A[Frontend (Angular 19)] -->|HTTP| B[API Gateway / Reverse Proxy] 
  B --> C[productos-service]
  B --> D[inventario-service]
  C -->|JDBC/ORM| E[(Postgres)]
  D -->|JDBC/ORM| E
  C --> F[Cache (Redis)]
  D --> F
  subgraph CI/CD
    G[GitHub Actions] --> H[Registry/Deploy]
  end
```

## Flujo de datos - Consulta de inventario

```mermaid
sequenceDiagram
  participant UI as Frontend
  participant API as API Gateway
  participant Prod as productos-service
  participant Inv as inventario-service
  UI->>API: GET /productos
  API->>Prod: GET /api/productos
  Prod-->>API: [lista productos]
  API->>Inv: GET /api/inventario?productoIds=[...]
  Inv-->>API: [stocks]
  API-->>UI: [productos con stock]
```

## Patrones de diseño implementados

### Patrón Repository
```mermaid
classDiagram
    class ProductoController {
        +ProductoService service
        +findAll()
        +findById(id)
        +create(dto)
    }
    class ProductoService {
        +ProductoRepository repo
        +findAll()
        +findById(id)
    }
    class ProductoRepository {
        +save(entity)
        +findById(id)
    }
    ProductoController --> ProductoService
    ProductoService --> ProductoRepository
```

### Circuit Breaker Pattern
```mermaid
flowchart LR
    A[Cliente] -->|Request| B{Circuit Breaker}
    B -->|Closed| C[Service]
    B -->|Open| D[Fallback]
    B -->|Half-Open| C
```

## Componentes principales

### Microservicios
1. productos-service:
   - Gestión de productos
   - CRUD operaciones
   - Cache de productos frecuentes

2. inventario-service:
   - Control de stock
   - Reserva de inventario
   - Notificaciones de bajo stock

### Frontend Angular
- Módulos lazy-loaded
- State management (servicios)
- Componentes reutilizables
- Interceptores HTTP

## Herramientas de desarrollo recomendadas

### Diagramación
- **Mermaid**: Diagramas en Markdown (usado aquí)
- **PlantUML**: Diagramas más detallados
- **draw.io**: Diagramas visuales
- **Structurizr**: Diagramas C4

### Monitoreo y observabilidad
```mermaid
flowchart LR
    A[Aplicación] -->|Métricas| B[Prometheus]
    A -->|Logs| C[ELK Stack]
    A -->|Traces| D[Jaeger]
    B --> E[Grafana]
    C --> E
    D --> E
```

## Escalabilidad y alta disponibilidad

### Arquitectura propuesta para producción
```mermaid
flowchart TB
    LB[Load Balancer] --> API1[API Gateway 1]
    LB --> API2[API Gateway 2]
    subgraph "Productos Service"
    API1 --> P1[Productos 1]
    API1 --> P2[Productos 2]
    API2 --> P1
    API2 --> P2
    end
    subgraph "Inventario Service"
    API1 --> I1[Inventario 1]
    API1 --> I2[Inventario 2]
    API2 --> I1
    API2 --> I2
    end
    subgraph "Cache Layer"
    P1 --> R1[Redis Cluster]
    P2 --> R1
    I1 --> R1
    I2 --> R1
    end
    subgraph "Database"
    R1 --> DB[(PostgreSQL HA)]
    end
```

## Notas técnicas adicionales

### Seguridad
- Autenticación: OAuth2/JWT
- TLS en todas las conexiones
- Rate limiting en API Gateway
- Secrets en HashiCorp Vault

### CI/CD
- GitHub Actions para CI
- Docker Registry para imágenes
- Deploy automatizado a staging
- Pruebas de integración