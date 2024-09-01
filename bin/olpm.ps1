if (-not $args[0]) {
    Write-Host "Usage: olpm [-v | --version]"
    exit 1
}

if ($args[0] -eq '-v' -or $args[0] -eq '--version') {
    Write-Host "0.0.1"
} else {
    Write-Host "Invalid argument: $($args[0])"
    Write-Host "Usage: olpm [-v | --version]"
    exit 1
}
