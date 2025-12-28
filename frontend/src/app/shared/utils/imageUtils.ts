export function resizeIfNeeded(path: string, maxWidth: number, maxHeight: number): Promise<string> {
  return new Promise((resolve, reject) => {
    const img = new Image();
    img.src = path;

    img.onload = () => {
      let width = img.width;
      let height = img.height;

      // Check if resizing is needed
      if (width <= maxWidth && height <= maxHeight) {
        // No resize needed, return original path
        resolve(path);
        return;
      }

      // Resize proportionally
      if (width > height) {
        if (width > maxWidth) {
          height = Math.round((height * maxWidth) / width);
          width = maxWidth;
        }
      } else {
        if (height > maxHeight) {
          width = Math.round((width * maxHeight) / height);
          height = maxHeight;
        }
      }

      // Draw resized image in canvas
      const canvas = document.createElement('canvas');
      canvas.width = width;
      canvas.height = height;
      const ctx = canvas.getContext('2d');
      ctx?.drawImage(img, 0, 0, width, height);

      // Return resized image as base64
      resolve(canvas.toDataURL('image/jpeg', 0.8));
    };

    img.onerror = (err) => reject(err);
  });
}
