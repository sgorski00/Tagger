import {Component, signal} from '@angular/core';
import {RouterLink} from "@angular/router";
import {DatePipe} from "@angular/common";
import {BasePage} from "../../../../shared/base-page/base-page";
import {HistoryPage} from "../../history/history-page/history-page";

@Component({
    selector: 'app-user-page',
    imports: [
        BasePage,
        RouterLink,
        DatePipe
    ],
    templateUrl: './profile-page.html',
    styleUrl: './profile-page.scss',
})
export class ProfilePage {
    protected readonly headerLabel = 'Profile';

    protected readonly accountData = signal({
        username: 'john_doe',
        email: 'john.doe@example.com',
        createdAt: new Date('2024-01-15'),
        firstName: 'John',
        lastName: 'Doe'
    });

    protected readonly historyItems = signal([
        { id: 1, title: 'Vintage Leather Jacket', date: new Date('2025-02-01') },
        { id: 2, title: 'Samsung Galaxy S23', date: new Date('2025-01-28') },
        { id: 3, title: 'Nike Air Max 90', date: new Date('2025-01-25') },
        { id: 4, title: 'MacBook Pro 16"', date: new Date('2025-01-20') },
    ]);
    protected readonly HistoryPage = HistoryPage;
}
