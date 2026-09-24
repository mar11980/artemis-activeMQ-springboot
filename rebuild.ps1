$ErrorActionPreference = "Stop"

Write-Host "=== Stopping Docker Desktop ===" -ForegroundColor Cyan

# Stop Docker Desktop
docker desktop stop

# Give Docker Desktop a moment to shut down
Start-Sleep -Seconds 5

Write-Host "=== Removing Docker WSL disk ===" -ForegroundColor Yellow

$dockerDisk = "C:\Users\marce\AppData\Local\Docker\wsl\disk\docker_data.vhdx"

if (Test-Path $dockerDisk) {
    Remove-Item -Path $dockerDisk -Force
    Write-Host "Deleted: $dockerDisk" -ForegroundColor Green
}
else {
    Write-Host "Docker disk not found: $dockerDisk" -ForegroundColor DarkYellow
}

Write-Host "=== Starting Docker Desktop ===" -ForegroundColor Cyan

docker desktop start

# Wait for Docker to become available
Write-Host "Waiting for Docker..." -ForegroundColor Yellow

do {
    Start-Sleep -Seconds 2

    try {
        docker info *> $null
        $dockerReady = $true
    }
    catch {
        $dockerReady = $false
    }

} while (-not $dockerReady)

Write-Host "Docker is ready." -ForegroundColor Green

Write-Host "=== Docker Compose down ===" -ForegroundColor Cyan

docker compose down -v

Write-Host "=== Removing unused Docker images ===" -ForegroundColor Cyan

docker image prune -a -f

Write-Host "=== Maven clean package ===" -ForegroundColor Cyan

mvn clean package -DskipTests

Write-Host "=== Docker Compose build and start ===" -ForegroundColor Cyan

docker compose up --build

Write-Host "=== Done ===" -ForegroundColor Green
