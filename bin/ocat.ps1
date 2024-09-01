function Show-Usage {
    Write-Host "Usage: ocat [-v | --version | file path] [additional arguments...]"
}

if (-not $args[0]) {
    Show-Usage
    exit 1
}

if ($args[0] -eq '-v') {
    Write-Host "0.1.1"
    exit 0
} elseif ($args[0] -eq '--version') {
    Write-Host "0.0.2"
    exit 0
} else {
    $jarPath = "C:\Orange Cat\Orange-cat-tools\lib\Ocat.jar"
    if (-not (Test-Path $jarPath)) {
        Write-Host "Error: Ocat.jar not found at $jarPath."
        exit 1
    }

    $additionalArgs = $args -join ' '
    Start-Process "java" -ArgumentList "-jar `"$jarPath`" $additionalArgs" -NoNewWindow -Wait
}

