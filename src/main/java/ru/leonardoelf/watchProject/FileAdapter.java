package ru.leonardoelf.watchProject;

public abstract class FileAdapter implements FileListener {
    @Override
    public void onCreated(FileEvent event) {
        //реализация не предусмотрена
    }
    @Override
    public void onModified(FileEvent event) {
        //реализация не предусмотрена
    }
    @Override
    public void onDeleted(FileEvent event) {
        //реализация не предусмотрена
    }
}