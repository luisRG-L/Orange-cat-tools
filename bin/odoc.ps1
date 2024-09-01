if (-not $args[0]) {
    Write-Host "Usage: script.ps1 [-v | --version] [other arguments...]"
    exit 1
}

if ($args[0] -eq '-v' -or $args[0] -eq '--version') {
    Write-Host "0.1.1"
} else {
    $jarPath = "C:\Orange Cat\Orange-cat-tools\lib\OdocToHtml.jar"
    if (-not (Test-Path $jarPath)) {
        Write-Host "Error: OdocToHtml.jar not found."
        exit 1
    }
    Start-Process "java" -ArgumentList "-jar `"$jarPath`" $($args -join ' ')" -NoNewWindow -Wait
}
