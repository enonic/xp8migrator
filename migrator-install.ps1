$repo = "enonic/xp8migrator"
$url = "https://github.com/$repo/releases/latest/download/migrator-windows.zip"
$zip = "$env:TEMP\migrator-windows.zip"

Write-Host "Downloading migrator-windows.zip..."
Invoke-WebRequest -Uri $url -OutFile $zip
Expand-Archive -Path $zip -DestinationPath . -Force
Remove-Item $zip
Write-Host "Installed .\migrator.exe"
